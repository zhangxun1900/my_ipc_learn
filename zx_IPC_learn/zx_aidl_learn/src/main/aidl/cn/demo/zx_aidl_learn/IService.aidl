// IService.aidl
package cn.demo.zx_aidl_learn;

import cn.demo.zx_aidl_learn.domain.Book;
import cn.demo.zx_aidl_learn.IOnNewBookArrivedListener;

interface IService {
    List<Book>getBookList();
    void addBook(in Book b);

    void registerListener(in IOnNewBookArrivedListener listener);
    void unregisterListener(in IOnNewBookArrivedListener listener);
}
