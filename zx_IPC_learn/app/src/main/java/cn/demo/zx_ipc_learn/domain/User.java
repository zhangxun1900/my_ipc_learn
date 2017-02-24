package cn.demo.zx_ipc_learn.domain;

import java.io.Serializable;

/**
 * @author Administrator
 * @version $Rev$
 * @time ${DATA} 17:52
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 * Created by xun on 2017/2/20.
 */

public class User implements Serializable {

    /**
     * 即使不设置serialVersion也能序列化，因为系统会随机提供一个serialVersionUID，但是却不能反序化。
     * 因为SerialVersionUID的工作机制是这样的：序列化的时候系统会把当前类的serialVersionUID
     * 写入序列化的文件中（也可能是其他中介），当反序列化的时候，系统会去检测文件中的serialVersionUID，
     * 看它是否和当前类的serialVersionUID一致，如果一致就是说明序列化的类的版本和当前类的版本
     * 是相同的，这个时候可以成功序列化。因此想要反序列化，那么就得手动设置serialVersionUID的值。
     * 当我们手动指定了它之后，我们很大程度上避免反序列化失败，例如删除或者增加了新的成员变量的时候，反序列化
     * 也能成功。但是当类的结构发生了根本性变化之后，即使serialVersionUID验证通过，反序列化也会失败，
     * 例如，修改了类名，修改了成员变量的类型等等。
     */
    private static final long serialVersionUID = 1L;

    public int userId;
    public String userName;
    public boolean isMale;

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                '}';
    }
}
