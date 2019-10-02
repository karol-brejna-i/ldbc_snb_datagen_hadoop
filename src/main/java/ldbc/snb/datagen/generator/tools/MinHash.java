/* 
 Copyright (c) 2013 LDBC
 Linked Data Benchmark Council (http://www.ldbcouncil.org)
 
 This file is part of ldbc_snb_datagen.
 
 ldbc_snb_datagen is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 ldbc_snb_datagen is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with ldbc_snb_datagen.  If not, see <http://www.gnu.org/licenses/>.
 
 Copyright (C) 2011 OpenLink Software <bdsmt@openlinksw.com>
 All Rights Reserved.
 
 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation;  only Version 2 of the License dated
 June 1991.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.*/
package ldbc.snb.datagen.generator.tools;


import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class MinHash<T> {

    private int numHashes_ = 0;
    private int a[];
    private int b[];
    private int p[];

    public MinHash(int numHashes) {
        numHashes_ = numHashes;
        Random random_ = new Random();
        a = new int[numHashes];
        b = new int[numHashes];
        p = new int[numHashes];
        for (int i = 0; i < numHashes; ++i) {
            a[i] = random_.nextInt();
            b[i] = random_.nextInt();
            p[i] = random_.nextInt();
        }
    }

    public ArrayList<Long> minHash(Set<Long> set) {
        ArrayList<Long> minHashes = new ArrayList<Long>();
        for (int i = 0; i < numHashes_; ++i) {
            long min = Long.MAX_VALUE;
            long minl = 0;
            for (Long l : set) {
                long hash = (a[i] * l + b[i]) % p[i];
                if (hash <= min) {
                    min = hash;
                    minl = l;

                }
            }
            minHashes.add(minl);
        }
        return minHashes;
    }
}
