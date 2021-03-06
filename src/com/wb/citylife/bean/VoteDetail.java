package com.wb.citylife.bean;

import java.util.List;

public class VoteDetail extends BaseBean{

    public String id;
    public int type;
    public String title;
    public String summary;
    public String thumbnailUrl;
    public String time;
    public int participantNum;
    public int questionNum;
    public List<QuestionItem> datas;
    		
    public class QuestionItem { 
    	public String questionId;
    	public String questionTitle;
    	public int questionType;
    	public String[] questionOptions;
    	public String[] questionOptionIds;
    	public boolean hasVote;
    	public String[] result;
    	public int[] rate;
    }
	
}
