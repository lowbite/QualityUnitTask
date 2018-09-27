package com.qualityunit.task.model;

public class Question {
    private int questionTypeId;
    private int categoryId;
    private int subcategoryId;

    public Question() {
    }

    public Question(int questionTypeId, int categoryId, int subcategoryId) {
        this.questionTypeId = questionTypeId;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (questionTypeId != question.questionTypeId) return false;
        if (categoryId != question.categoryId) return false;
        return subcategoryId == question.subcategoryId;
    }

    @Override
    public int hashCode() {
        int result = questionTypeId;
        result = 31 * result + categoryId;
        result = 31 * result + subcategoryId;
        return result;
    }

    public static Question fromString(String serviceStr){
        Question question = new Question();
        String[] idStrings = serviceStr.split("\\.");
        for (int i = 0; i < idStrings.length; i++) {
            switch (i) {
                case 0:
                    question.setQuestionTypeId(Integer.valueOf(idStrings[i]));
                    break;
                case 1:
                    question.setCategoryId(Integer.valueOf(idStrings[i]));
                    break;
                case 2:
                    question.setSubcategoryId(Integer.valueOf(idStrings[i]));
                    break;
            }
        }
        return question;
    }
}
