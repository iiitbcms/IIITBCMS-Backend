package com.spe.iiitbcms.service;

import com.spe.iiitbcms.dto.SubpostDto;
import com.spe.iiitbcms.exceptions.CMSException;
import com.spe.iiitbcms.mapper.SubpostMapper;
import com.spe.iiitbcms.model.Subpost;
import com.spe.iiitbcms.model.User;
import com.spe.iiitbcms.repository.SubpostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SubpostService {

    private final SubpostRepository subpostRepository;
    private final SubpostMapper subpostMapper;
    private final AuthService authService;

    @Transactional
    public Subpost save(SubpostDto subpostDto)
    {
        User user = authService.getCurrentUser();
        if(user.getRole().equals("admin"))
        {
            Subpost subpost = new Subpost();

            subpost.setDescription(subpostDto.getDescription());
            subpost.setName(subpostDto.getName());
            subpost.setCreatedDate(Instant.now());

            subpostRepository.save(subpost);

            return subpost;
        }
        else
        {
            return null;
        }

    }

    public List<Subpost> getAll(HttpServletRequest request)
    {
        User user = authService.getCurrentUser();
        System.out.println("role is " + user.getRole() );
        if(user.getRole().equals("admin"))
            return subpostRepository.findAll();
        else
        {
            return null;
        }
    }

    public SubpostDto getSubpost(Long id) {
        Subpost subpost = subpostRepository.findById(id)
                .orElseThrow(() -> new CMSException("No subpost found with ID - " + id));
        return subpostMapper.mapSubpostToDto(subpost);
    }

    public boolean deleteSubPost(String subpost)
    {
        User user = authService.getCurrentUser();
        if(user.getRole().equals("admin"))
        {
            Subpost subpostToBeDeleted = subpostRepository.getByName(subpost);
            if(subpostToBeDeleted!=null)
            {
                subpostRepository.delete(subpostToBeDeleted);
                return true;
            }
            return false;
        }
        else
        {
            return false;
        }


    }
}
