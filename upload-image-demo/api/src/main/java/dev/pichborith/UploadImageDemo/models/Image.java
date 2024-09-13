package dev.pichborith.UploadImageDemo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "images")

public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Column(name = "image_data")
    private byte[] data;

    // Constructors, getters, and setters
    public Image() {}

    public Image(byte[] data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Image {" +
                   "id=" + id +
                   '}';
    }
}
