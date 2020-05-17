package com.davidkazad.cevv.models;

import com.davidkz.eazyorm.Model;
import com.davidkz.eazyorm.annotation.Column;

public class Page{ //extends Model{

    //@Column(name = "pid", unique = true, onUniqueConflict = Column.ConflictAction.IGNORE)
    private int id;
    //@Column(name = "title")
    private String title;
    //@Column(name = "content")
    private String content;
    //@Column(name = "number")
    private String number;
    //@Column(name = "bookId")
    private int bookId;

    public Page() {
    }

    public Page(int id, String number, String title, String content, int bookId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.number = number;
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPid() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
