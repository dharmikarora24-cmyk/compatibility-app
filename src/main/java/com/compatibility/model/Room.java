package com.compatibility.model;

public class Room {
    private String roomCode;
    private Integer[] girlAnswers;
    private Integer[] boyAnswers;
    private boolean girlDone = false;
    private boolean boyDone = false;
    private int[] questionIndices;

    public Room(String roomCode) {
        this.roomCode = roomCode;
        this.girlAnswers = new Integer[10];
        this.boyAnswers = new Integer[10];
    }

    public String getRoomCode() { return roomCode; }
    public Integer[] getGirlAnswers() { return girlAnswers; }
    public Integer[] getBoyAnswers() { return boyAnswers; }
    public boolean isGirlDone() { return girlDone; }
    public boolean isBoyDone() { return boyDone; }
    public boolean isBothDone() { return girlDone && boyDone; }
    public int[] getQuestionIndices() { return questionIndices; }
    public void setQuestionIndices(int[] indices) { this.questionIndices = indices; }

    public void setGirlAnswers(Integer[] answers) {
        this.girlAnswers = answers;
        this.girlDone = true;
    }

    public void setBoyAnswers(Integer[] answers) {
        this.boyAnswers = answers;
        this.boyDone = true;
    }
}
