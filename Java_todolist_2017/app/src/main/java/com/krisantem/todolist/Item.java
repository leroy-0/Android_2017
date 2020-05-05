package com.krisantem.todolist;

public class Item {
    private int     id;
    private String title;
    private String content;
    private String date;
    private String time;
    private String status;

    /**
     * @param values
     */
    Item(String[] values) {
        super();
        this.id = Integer.parseInt(values[0]);
        this.title = values[1];
        this.content = values[2];
        this.date = values[3];
        this.time = values[4];
        this.status = values[5];
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    String getContent() {
        return content;
    }

    String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
