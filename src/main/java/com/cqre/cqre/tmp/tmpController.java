package com.cqre.cqre.tmp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class tmpController {

    @PersistenceContext
    EntityManager em;
    private final tmpRepository tmpRepository;

    @GetMapping("/delete")
    @Transactional
    /*기존 로직 insert -> select -> delete 반복*/
    public String delete() {
        tmp a = new tmp();

        tmpRepository.save(a);

        em.flush(); // insert 쿼리(추천)
        em.clear();

        tmpRepository.delete(a); // select 쿼리 + delete 쿼리 (추천 취소)

        return "/home/home";
    }

    int num = 5;
    @GetMapping("/update")
    @Transactional
    /*변경 예정 로직 insert -> select -> update 반복*/
    public String update() {
        tmp a = new tmp();

        tmpRepository.save(a);

        em.flush(); // insert 쿼리(추천)
        em.clear();

        List<tmp> all = tmpRepository.findAll(); // select 쿼리 (조회)
        tmp tmp = all.get(0);

        tmp.setNum(num++);
        em.flush(); // update 쿼리 (추천 취소)
        em.clear();

        return "/home/home";
    }
}
