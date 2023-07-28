package com.green.growgreen.diary;

import com.green.growgreen.diary.model.*;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DiaryMapperTest {

    @Autowired
    private DiaryMapper MAPPER;

    @Test
    void insDiary() {
        DiaryEntity entity = new DiaryEntity();
        entity.setTitle("title_test");
        entity.setCtnt("test");

        int result = MAPPER.insDiary(entity);
        assertEquals(1,result);
        assertNotEquals(0,entity.getIdiary());
        assertEquals("test", entity.getCtnt());
        assertEquals("title_test", entity.getTitle());

        DiaryEntity entity2 = new DiaryEntity();
        entity2.setTitle("title_test2");
        entity2.setCtnt("test2");

        int result2 = MAPPER.insDiary(entity2);
        assertEquals(1,result2);
        assertNotEquals(0,entity2.getIdiary());
        assertEquals(1,entity2.getIdiary() - entity.getIdiary());

        DiarySelDetailDto dto = new DiarySelDetailDto(entity.getIdiary());
        DiarySelAllVo vo = MAPPER.selDiaryById(dto);
        assertEquals(entity.getIdiary(), vo.getIdiary());
        assertEquals("test", vo.getCtnt());
    }

    @Test
    void delDiary() {
        DiaryDelDto dto = new DiaryDelDto();
        dto.setIdiary(1);
        int result = MAPPER.delDiary(dto);
        assertEquals(1, result);

        int result2 = MAPPER.delDiary(dto);
        assertEquals(0, result2);

        List<DiarySelAllVo> list = MAPPER.selDiaryAll();
        assertEquals(9, list.size());
    }

    @Test
    void updDiary() {
        DiaryEntity entity = new DiaryEntity();
        entity.setIdiary(1);
        entity.setCtnt("qwe");
        entity.setTitle("poi");
        entity.setPic("zzzz.png");

        int result = MAPPER.updDiary(entity);
        assertEquals(1, result);

        DiarySelDetailDto dto = new DiarySelDetailDto(entity.getIdiary());
        DiarySelAllVo vo = MAPPER.selDiaryById(dto);

        assertEquals(entity.getIdiary(), vo.getIdiary());
        assertEquals(entity.getTitle(),vo.getTitle());
        assertEquals(entity.getPic(), vo.getPic());
        assertEquals(entity.getCtnt(), vo.getCtnt());
        assertEquals("2023-01-15", vo.getCreatedAt());
    }

    @Test
    void selDiaryAll() {
        List<DiarySelAllVo> list = MAPPER.selDiaryAll();
        assertEquals(10, list.size());

//        DiarySelAllVo vo = list.get(0);
//        assertEquals(vo.getIdiary(), 11);
//        assertEquals(vo.getTitle(), "물주기");
//        assertEquals(vo.getCtnt(), "물주기");
//
//        DiarySelAllVo vo1 = list.get(1);
//        assertEquals(vo1.getIdiary(), 10);
//        assertEquals(vo1.getCtnt(), "처음보는 식물인 파키라 익숙하지 않지만 지금까지 경험을 살려서 이쁘게 키워줄게 우선 못생긴 집 이사부터 가자!!");
//        assertEquals(vo1.getTitle(), "파키라와 첫 만남");

        for (DiarySelAllVo vo : list ) {
            DiarySelDetailDto dto = new DiarySelDetailDto(vo.getIdiary());
            DiarySelAllVo item = MAPPER.selDiaryById(dto);

            assertEquals(item.getCtnt(), vo.getCtnt());
            assertEquals(item.getPic(), vo.getPic());
            assertEquals(item.getIdiary(), vo.getIdiary());
            assertEquals(item.getCreatedAt(), vo.getCreatedAt());
            assertEquals(item.getTitle(), vo.getTitle());
        }

    }

    @Test
    void selDiaryById() {
        DiarySelDetailDto dto = new DiarySelDetailDto(1);
        DiarySelAllVo result = MAPPER.selDiaryById(dto);
        assertEquals(1, result.getIdiary());
        assertEquals("카스테라 잎이 갈라진 날", result.getTitle());
        assertEquals("카스테라의 잎이 드디어 갈라지기 시작했다. 붙어있던 잎이 갈라지는게 넘나 신기!\n", result.getCtnt());
        assertEquals("2023-01-15", result.getCreatedAt());
        assertEquals("fc4e7756-6f7d-4e6e-8aac-d0b2e46f0c16.jpg", result.getPic());

        DiarySelDetailDto dto2 = new DiarySelDetailDto(2);
        DiarySelAllVo result2 = MAPPER.selDiaryById(dto2);
        assertEquals(2, result2.getIdiary());
        assertEquals("향기가 굉장해 엄청나!", result2.getTitle());
        assertEquals("데려온 날부터 쑥쑥 잘자라준 허브티. 혼자 잘 자라고 공기정화도 해주고..효자다 효자 장미향이 은은한게 너무 좋아~~~\n", result2.getCtnt());
        assertEquals("2023-02-20", result2.getCreatedAt());
        assertEquals("6458c54c-6fbb-4dfb-b848-7bc38e50accc.jpg", result2.getPic());
    }

    @Test
    void testInsDiary() {
    }

    @Test
    void testDelDiary() {
    }

    @Test
    void testUpdDiary() {
    }

    @Test
    void testSelDiaryAll() {
    }

    @Test
    void testSelDiaryById() {
    }
}