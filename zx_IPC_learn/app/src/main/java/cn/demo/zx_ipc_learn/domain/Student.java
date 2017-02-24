package cn.demo.zx_ipc_learn.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 * @version $Rev$
 * @time ${DATA} 18:58
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 * Created by xun on 2017/2/20.
 */

public class Student implements Parcelable {
    public String name;
    public boolean isMale;

    /**
     * Book类也是一个继承了Parcelable的类
     */
    public Book book;

    public Student(String name, boolean isMale, Book book) {
        this.name = name;
        this.isMale = isMale;
        this.book = book;
    }

    /**
     * Parcel内部包装了可序列化的数据，可以在Binder中自由传输
     * @param in
     */
    protected Student(Parcel in) {
        name = in.readString();
        isMale = in.readByte() != 0;
        book = in.readParcelable(Book.class.getClassLoader());
    }

    /**
     * 反序列化
     */
    public static final Creator<Student> CREATOR = new Creator<Student>() {

        /**
         * 创建序列化对象
         * @param in
         * @return
         */
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        /**
         * 创建序列号数据
         * @param size
         * @return
         */
        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    /**
     * 内容描述
     * 当当前对象中存在文件描述符时，此方法返回1
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 序列化
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isMale ? 1 : 0));
        dest.writeParcelable(book, flags);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", isMale=" + isMale +
                ", book=" + book.toString() +
                '}';
    }
}
