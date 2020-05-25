/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.pidev.entities;

/**
 *
 * @author ghada
 */
public class Bareme {
    private int id;
    private int note;
    private int min;
    private int max ;

    public Bareme(int id,int note, int min, int max) {
        this.id=id;
        this.note = note;
        this.min = min;
        this.max = max;
    }

    public Bareme(int note, int min, int max) {
        this.note = note;
        this.min = min;
        this.max = max;
    }

    public Bareme() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "Bareme{" + "id=" + id + ", note=" + note + ", min=" + min + ", max=" + max + '}';
    }
    
    
}
