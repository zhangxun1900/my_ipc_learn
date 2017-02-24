// IOnNewBookArrivedListener.aidl
package cn.demo.zx_aidl_learn;

// Declare any non-default types here with import statements
import cn.demo.zx_aidl_learn.domain.Book;
interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
