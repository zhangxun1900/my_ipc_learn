package cn.demo.zx_aidl_learn.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 * @version $Rev$
 * @time ${DATA} 16:05
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 * Created by xun on 2017/2/23.
 */

public class Book implements Parcelable{
    public int bookId;
    public String name;

    public Book(int bookId, String name) {
        this.bookId = bookId;
        this.name = name;
    }

    protected Book(Parcel in) {
        bookId = in.readInt();
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(name);
    }
}
