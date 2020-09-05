package com.cellway.Cellway.model;


import android.os.Parcel;
import android.os.Parcelable;

public class BookModel implements Parcelable {
    int img_front;
    int img_back;
    int img_side;
    int img_slope;

    String bookMobileImage;
    String bookimageId;

    public BookModel(){
    }

    protected BookModel(Parcel in) {
        img_front = in.readInt();
        img_back = in.readInt();
        img_side = in.readInt();
        img_slope = in.readInt();
        bookMobileImage = in.readString();
        bookimageId = in.readString();
    }

    public static final Creator<BookModel> CREATOR = new Creator<BookModel>() {
        @Override
        public BookModel createFromParcel(Parcel in) {
            return new BookModel(in);
        }

        @Override
        public BookModel[] newArray(int size) {
            return new BookModel[size];
        }
    };

    public String getBookimageId() {
        return bookimageId;
    }

    public void setBookimageId(String bookimageId) {
        this.bookimageId = bookimageId;
    }

    public String getBookMobileImage() {
        return bookMobileImage;
    }

    public void setBookMobileImage(String bookMobileImage) {
        this.bookMobileImage = bookMobileImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(img_front);
        parcel.writeInt(img_back);
        parcel.writeInt(img_side);
        parcel.writeInt(img_slope);
        parcel.writeString(bookMobileImage);
        parcel.writeString(bookimageId);
    }
}
