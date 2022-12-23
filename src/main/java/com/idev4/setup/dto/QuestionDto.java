package com.idev4.setup.dto;

import java.util.List;

public class QuestionDto {

    public long questionSequence;
    public String questionId;
    public String questionString;
    public long qstSortOrdr;
    public long questionKey;
    public long questionStatus;
    public String questionCategory;
    public long questionCategoryKey;
    public long questionnaireSequence;
    public List<AnswerDto> answers;
    public long answerSeq;
}
