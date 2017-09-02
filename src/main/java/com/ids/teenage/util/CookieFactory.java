package com.ids.teenage.util;

import org.apache.http.impl.client.BasicCookieStore;

/**
 * Created by sam.fu on 2017/6/30.
 */
public class CookieFactory {
    private  BasicCookieStore basicCookieStore;

    public BasicCookieStore getBasicCookieStore() {
        return basicCookieStore;
    }

    public void setBasicCookieStore(BasicCookieStore basicCookieStore) {
        this.basicCookieStore = basicCookieStore;
    }
}
