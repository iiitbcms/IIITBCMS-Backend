package com.spe.iiitbcms.service;

import com.spe.iiitbcms.dto.VoteDto;
import com.spe.iiitbcms.exceptions.PostNotFoundException;
import com.spe.iiitbcms.exceptions.CMSException;
import com.spe.iiitbcms.model.Post;
import com.spe.iiitbcms.model.User;
import com.spe.iiitbcms.model.Vote;
import com.spe.iiitbcms.repository.PostRepository;
import com.spe.iiitbcms.repository.UserRepository;
import com.spe.iiitbcms.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.spe.iiitbcms.model.VoteType.NO_VOTE;
import static com.spe.iiitbcms.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Transactional
    public void votes(VoteDto voteDto)
    {
        System.out.println(voteDto.getVoteType());
        System.out.println(voteDto.getUserEmail());
        System.out.println(voteDto.getPostId());

        Post post = postRepository.getById(voteDto.getPostId());
        User user = userRepository.getByEmail(voteDto.getUserEmail());
        Optional<Vote> voteByPostAndUser = voteRepository.findPostByVote(post, user);
        if(voteByPostAndUser.isPresent())
        {
            System.out.println("Name of the voted posted is " + voteByPostAndUser.get().getPost().getPostName());
            if(voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType()))
            {
                if (UPVOTE.equals(voteDto.getVoteType()))
                {
                    voteByPostAndUser.get().setVoteType(NO_VOTE);
                    post.setVoteCount(post.getVoteCount() - 1);
                }
                else
                {
                    voteByPostAndUser.get().setVoteType(NO_VOTE);
                    post.setVoteCount(post.getVoteCount() + 1);
                }
            }
            else if(voteByPostAndUser.get().getVoteType().equals(NO_VOTE))
            {
                voteByPostAndUser.get().setVoteType(voteDto.getVoteType());
                if (UPVOTE.equals(voteDto.getVoteType()))
                    post.setVoteCount(post.getVoteCount() + 1);
                else
                    post.setVoteCount(post.getVoteCount() - 1);
            }
            else if(!voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType()))
            {
                voteByPostAndUser.get().setVoteType(voteDto.getVoteType());
                if (UPVOTE.equals(voteDto.getVoteType()))
                    post.setVoteCount(post.getVoteCount() + 2);
                else
                    post.setVoteCount(post.getVoteCount() - 2);
            }
            postRepository.save(post);
            voteRepository.save(voteByPostAndUser.get());
        }
        else
        {
            Vote vote = new Vote();
            vote.setVoteType(voteDto.getVoteType());
            vote.setUser(user);
            vote.setPost(post);

            if (UPVOTE.equals(voteDto.getVoteType()))
                post.setVoteCount(post.getVoteCount() + 1);
            else
                post.setVoteCount(post.getVoteCount() - 1);

            voteRepository.save(vote);
            postRepository.save(post);
        }

    }


    @Transactional
    public void vote(VoteDto voteDto)
    {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        System.out.println("post is " + post.getPostName());
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new CMSException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
