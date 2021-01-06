package com.example.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.vo.BoardVO;

@Repository("boardDao")
public class BoardDaoImpl implements BoardDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertBoard(BoardVO boardVo) {
		this.sqlSession.insert("Board.insertSP", boardVo);
	}

	@Override
	public void selectBoard(Map map) {
		this.sqlSession.selectOne("Board.selectOneSP", map);
	}

	@Override
	public void selectAllBoard(Map map) {
		this.sqlSession.selectList("Board.selectAllSP", map);
	}

	@Override
	public void updateBoard(BoardVO boardVo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBoard(int idx) {
		// TODO Auto-generated method stub
		
	}
}
