package com.example.movie.nonEntity;

public class SeatInfo {
    private Integer screening_id;
    private Integer seat_id;
    private Integer row;
    private Integer col;
    private boolean taken_or_not;

    //getters and setters
    public Integer getScreening_id() {
        return screening_id;
    }

    public void setScreening_id(Integer screening_id) {
        this.screening_id = screening_id;
    }

    public Integer getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Integer seat_id) {
        this.seat_id = seat_id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public boolean isTaken_or_not() {
        return taken_or_not;
    }

    public void setTaken_or_not(boolean taken_or_not) {
        this.taken_or_not = taken_or_not;
    }
}
