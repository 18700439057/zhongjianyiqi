/*
 *
 *  *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>
 *
 *  *  AG-Enterprise 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  *  老A将追究授予人和传播人的法律责任!
 *
 *  *  This program is free software; you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation; either version 2 of the License, or
 *  *  (at your option) any later version.
 *
 *  *  This program is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *
 *  *  You should have received a copy of the GNU General Public License along
 *  *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.github.wxiaoqi.security.common.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;

/**
 * Created by ace on 2017/8/11.
 */
public class Sha256PasswordEncoder implements PasswordEncoder {

//    @Override
//    public String encode(CharSequence charSequence) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte digest[] = md.digest(charSequence.toString().getBytes("UTF-8"));
//            return new String(Base64.encodeBase64(digest));
//        } catch (Exception e) {
//            return null;
//        }
//    }
        @Override
        public String encode(CharSequence charSequence) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(charSequence.toString().getBytes());
                byte b[] = md.digest();

                int i;

                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if(i<0) i+= 256;
                    if(i<16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
                return buf.toString();
            } catch (Exception e) {
                return null;
            }
        }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return this.encode(charSequence.toString()).equals(s);
    }
}
