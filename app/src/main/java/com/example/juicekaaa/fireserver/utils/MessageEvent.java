package com.example.juicekaaa.fireserver.utils;

import com.example.juicekaaa.fireserver.net.ArchitectureBean;
import com.example.juicekaaa.fireserver.net.HistorRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author: ZhongMing
 * DATE: 2018/11/16 0016
 * Description:
 **/
public class MessageEvent {
    private int TAG;
    private String message;
    private List<String> listPath;
    private List<String> listPaths;
    private List<String> imageTitle;
    private List<String> imageTitles;
    private List<ArchitectureBean> a_list;
    private List<ArchitectureBean> b_list;
    private List<ArchitectureBean> c_list;
    private List<ArchitectureBean> d_list;
    private List<ArchitectureBean> e_list;
    private List<ArchitectureBean> f_list;
    private List<ArchitectureBean> g_list;
    private List<HistorRecordBean> lists;
    private byte[] buffer;

    public MessageEvent(int TAG) {
        this.TAG = TAG;
    }

    public MessageEvent(int TAG, String message) {
        this.TAG = TAG;
        this.message = message;
    }


    public List<String> getListPath() {
        return listPath;
    }

    public void setListPath(List<String> listPath) {
        this.listPath = listPath;
    }

    public List<String> getListPaths() {
        return listPaths;
    }

    public void setListPaths(List<String> listPaths) {
        this.listPaths = listPaths;
    }

    public List<String> getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(List<String> imageTitle) {
        this.imageTitle = imageTitle;
    }

    public List<String> getImageTitles() {
        return imageTitles;
    }

    public void setImageTitles(List<String> imageTitles) {
        this.imageTitles = imageTitles;
    }

    public int getTAG() {
        return TAG;
    }

    public void setTAG(int TAG) {
        this.TAG = TAG;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ArchitectureBean> getA_list() {
        return a_list;
    }

    public void setA_list(List<ArchitectureBean> a_list) {
        this.a_list = a_list;
    }

    public List<ArchitectureBean> getB_list() {
        return b_list;
    }

    public void setB_list(List<ArchitectureBean> b_list) {
        this.b_list = b_list;
    }

    public List<ArchitectureBean> getC_list() {
        return c_list;
    }

    public void setC_list(List<ArchitectureBean> c_list) {
        this.c_list = c_list;
    }

    public List<ArchitectureBean> getD_list() {
        return d_list;
    }

    public void setD_list(List<ArchitectureBean> d_list) {
        this.d_list = d_list;
    }

    public List<ArchitectureBean> getE_list() {
        return e_list;
    }

    public void setE_list(List<ArchitectureBean> e_list) {
        this.e_list = e_list;
    }

    public List<ArchitectureBean> getF_list() {
        return f_list;
    }

    public void setF_list(List<ArchitectureBean> f_list) {
        this.f_list = f_list;
    }

    public List<ArchitectureBean> getG_list() {
        return g_list;
    }

    public void setG_list(List<ArchitectureBean> g_list) {
        this.g_list = g_list;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public List<HistorRecordBean> getLists() {
        return lists;
    }

    public void setLists(List<HistorRecordBean> lists) {
        this.lists = lists;
    }
}
