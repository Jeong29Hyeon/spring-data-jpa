package com.study.datajpa.repository;

import com.study.datajpa.entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void saveTest() throws Exception {
        //given
        Item item = new Item();
        item.setName("아이템1");
        itemRepository.save(item);

        em.flush();
        em.clear();

        Item item1 = new Item();
        item1.setId(1L);
        itemRepository.save(item1);
        em.flush();
        //when

        //then
     }
}