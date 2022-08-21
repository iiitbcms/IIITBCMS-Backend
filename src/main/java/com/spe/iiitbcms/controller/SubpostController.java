package com.spe.iiitbcms.controller;

import com.spe.iiitbcms.dto.SubpostDto;
import com.spe.iiitbcms.model.Subpost;
import com.spe.iiitbcms.service.SubpostService;
import io.jsonwebtoken.Header;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/subpost")
@AllArgsConstructor
@Slf4j
public class SubpostController {

    private static final Logger logger = LogManager.getLogger(SubpostController.class);
    private final SubpostService subpostService;

    @PostMapping("/post")
    public ResponseEntity<Subpost> createSubpost(@RequestBody SubpostDto subpostDto) {
        Subpost subpost = new Subpost();
        HttpStatus stat;
        try
        {
            subpost = subpostService.save(subpostDto);
            stat = HttpStatus.CREATED;
            if(subpost==null)
            {
                stat = HttpStatus.FORBIDDEN;
                throw new Exception();
            }
            logger.info("Successfully created subpost");
        }
        catch (Exception e)
        {
            stat = HttpStatus.EXPECTATION_FAILED;
            logger.error("Could not create subpost");
        }

        return ResponseEntity.status(stat).body(subpost);
    }

    @GetMapping
    public ResponseEntity<List<Subpost>> getAllSubposts(HttpServletRequest request)
    {

        HttpStatus stat;
        List<Subpost> subposts = new ArrayList<>();
        try {
            subposts = subpostService.getAll(request);
            stat = HttpStatus.OK;
            if(subposts==null)
            {
                stat = HttpStatus.FORBIDDEN;
                throw new Exception();
            }
            logger.info("Successfully fetched subposts");
        } catch (Exception e) {
            stat = HttpStatus.EXPECTATION_FAILED;
            logger.error("Error in fetching subposts");
        }
        return ResponseEntity.status(stat).body(subposts);
    }

    @GetMapping("{id}")
    public ResponseEntity<SubpostDto> getSubpost(@PathVariable Long id) {
        HttpStatus stat;
        SubpostDto subpost = new SubpostDto();
        try {
            subpost = subpostService.getSubpost(id);
            stat = HttpStatus.OK;
            logger.info("Successfully fetched subpost with id " + id);
        } catch (Exception e) {
            stat = HttpStatus.EXPECTATION_FAILED;
            logger.error("Error in fetching subpost with id " + id);
        }
        return ResponseEntity.status(stat).body(subpost);
    }

    @DeleteMapping("{subpost}")
    public ResponseEntity<HttpStatus> deleteSubPost(@PathVariable String subpost)
    {
        System.out.println("role is " + subpost);
        if(subpostService.deleteSubPost(subpost))
        {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }
}
