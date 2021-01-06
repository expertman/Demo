package com.example.dao;

import java.util.Map;

import com.example.vo.BoardVO;

public interface BoardDao {
	void insertBoard(BoardVO boardVo);
	void selectBoard(Map map);
	void selectAllBoard(Map map);
	void updateBoard(BoardVO boardVo);
	void deleteBoard(int idx);
}
