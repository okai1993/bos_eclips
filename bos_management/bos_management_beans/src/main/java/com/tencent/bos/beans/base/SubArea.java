package com.tencent.bos.beans.base;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

/**
 * @description:分区
 */
@Entity
@Table(name = "T_SUB_AREA")
public class SubArea {

    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Long id;
    @Column(name = "C_START_NUM")
    private String startNum; // 起始号
    @Column(name = "C_ENDNUM")
    private String endNum; // 终止号
    @Column(name = "C_SINGLE")
    private Character single; // 单双号
    @Column(name = "C_KEY_WORDS")
    private String keyWords; // 关键字
    @Column(name = "C_ASSIST_KEY_WORDS")
    private String assistKeyWords; // 辅助关键字

    @ManyToOne
    @JoinColumn(name = "C_AREA_ID")
    /*@LazyCollection(LazyCollectionOption.FALSE)*/
    private Area area; // 区域
    @ManyToOne
    @JoinColumn(name = "C_FIXEDAREA_ID")
    private FixedArea fixedArea; // 定区

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getSingle() {
        return single;
    }

    public void setSingle(Character single) {
        this.single = single;
    }

    public String getStartNum() {
        return startNum;
    }

    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }

    public String getEndNum() {
        return endNum;
    }

    public void setEndNum(String endNum) {
        this.endNum = endNum;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getAssistKeyWords() {
        return assistKeyWords;
    }

    public void setAssistKeyWords(String assistKeyWords) {
        this.assistKeyWords = assistKeyWords;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public FixedArea getFixedArea() {
        return fixedArea;
    }

    public void setFixedArea(FixedArea fixedArea) {
        this.fixedArea = fixedArea;
    }

}
