package com.pme.post.repo;

import com.pme.post.dto.PostInfo;
import com.pme.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    @Query(value =
            "select new com.pme.post.dto.PostInfo(p.id, p.title, p.description, p.publicationDate) " +
                    " from Post p where p.user_id=?1 and p.publicationDate<=?2 order by p.publicationDate desc " +
                    " limit ?3 offset ?4")
    List<PostInfo> getAllPostsByUser1(Long userId, LocalDateTime endDate, int limit, int offset);

    @Query(value =
            "select new com.pme.post.dto.PostInfo(p.id, p.title, p.description, p.publicationDate) " +
                    " from Post p where p.user_id=?1 and p.publicationDate between ?2 and ?3 " +
                    " order by p.publicationDate desc limit ?4 offset ?5")
    List<PostInfo> getAllPostsByUser2(Long userId, LocalDateTime startDate, LocalDateTime endDate, int limit, int offset);

}
