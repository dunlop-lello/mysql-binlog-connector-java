/*
 * Copyright 2016 Phil Lello <phil@dunlop-lello.uk>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.shyiko.mysql.binlog.network.protocol;

import com.github.shyiko.mysql.binlog.io.ByteArrayInputStream;
import java.io.IOException;

/**
 *
 * @author Phil Lello <phil@dunlop-lello.uk>
 */
public class ResultSetColumnPacket {

    String catalog;
    String schema;
    String table;
    String org_table;
    String name;
    String org_name;
    int next_length;
    private int character_set;
    private int column_length;
    private int type;
    private int flags;
    private int decimals;

    public ResultSetColumnPacket(byte[] bytes) throws IOException {
        ByteArrayInputStream buffer = new ByteArrayInputStream(bytes);
        catalog = buffer.readLengthEncodedString();
        schema = buffer.readLengthEncodedString();
        table = buffer.readLengthEncodedString();
        org_table = buffer.readLengthEncodedString();
        name = buffer.readLengthEncodedString();
        org_name = buffer.readLengthEncodedString();
        if ((next_length = buffer.readPackedInteger()) != 0x0C) {
            throw new IOException("Unexpected next_length "+next_length);
        }
        character_set = buffer.readInteger(2);
        column_length = buffer.readInteger(4);
        type = buffer.readInteger(1);
        flags = buffer.readInteger(2);
        decimals = buffer.readInteger(1);
    }

    public String catalog() {
        return catalog;
    }

    public String schema() {
        return schema;
    }

    public String name() {
        return name;
    }

    public String toString() {
        return "ResultSetColumnPacket{" + "catalog=" + catalog + ", schema=" + schema + ", table=" + table + ", org_table=" + org_table + ", name=" + name + ", org_name=" + org_name + ", next_length=" + next_length + ", character_set=" + character_set + ", column_length=" + column_length + ", type=" + type + ", flags=" + flags + ", decimals=" + decimals + '}';
    }

}
