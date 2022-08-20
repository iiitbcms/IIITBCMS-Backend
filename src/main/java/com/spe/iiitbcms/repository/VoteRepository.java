package com.spe.iiitbcms.repository;

import com.spe.iiitbcms.model.Post;
import com.spe.iiitbcms.model.User;
import com.spe.iiitbcms.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>
{

    @Query("select u from Vote u where u.post = ?1 and u.user=?2")
    Optional<Vote> findPostByVote(Post post, User user);

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
