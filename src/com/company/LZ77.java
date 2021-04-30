package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LZ77 {

    public int windowSize;
    public int lookAheadBufferSize;
    public LZ77(int windowSize, int lookAheadBufferSize) {
            this.windowSize = windowSize;
            this.lookAheadBufferSize = lookAheadBufferSize;
        }

        private String txt;

        public static class Tag {
            public int offset, length;
            public char nextSymbol;

            public Tag(int offset, int length, char nextSymbol) {
                this.offset = offset;
                this.length = length;
                this.nextSymbol = nextSymbol;
            }

            public Tag(String tagStr) {
                String[] values = tagStr.split(",");
                offset = Integer.parseInt(Character.toString( values[0].charAt(1) ));
                length = Integer.parseInt(values[1]);
                nextSymbol = values[2].charAt(0);
            }
            public String toString() {
                return "<" + offset + "," + length + "," + nextSymbol + ">";
            }
        }
        public Tag getMax(int bufferStart) {
            int windowStart = bufferStart - windowSize;//byshof 3dd el chars elly 2bl kda
            int bufferEnd = bufferStart + lookAheadBufferSize;
            int maxLength = 0, offset = 0;

            if (windowStart < 0) {
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                windowStart = 0;
            }
            if (bufferEnd > txt.length()) {
                bufferEnd = txt.length();
            }

            for (int i = windowStart; i < bufferEnd; ++i) {
                int a = i, b = bufferStart;
                while (a < b && a < bufferEnd && b < bufferEnd && txt.charAt(a) == txt.charAt(b)) {// b3ml search law el char mo3ayeen  mawgood 2bl kda
                    a++; b++;
                }
                if (b - bufferStart > maxLength) {
                    maxLength = b - bufferStart;
                    offset = bufferStart - i;
                }
            }
            int nextCharIdx = bufferStart + maxLength;
            return new Tag(offset, maxLength, nextCharIdx < txt.length() ? txt.charAt(nextCharIdx) : 0);
        }

        public ArrayList<Tag> encode(String txt) {
            ArrayList<Tag> tags = new ArrayList<Tag>();
            this.txt = txt;
            for (int cur = 0; cur < txt.length();) {
                Tag ret = getMax(cur);
                tags.add(ret);
                cur += ret.length + 1;
            }
            return tags;
        }

        public String decode(ArrayList<Tag> tags) {
            String result = "";
            for (Tag tag : tags) {
                int c = result.length();
                for (int i = 0; i < tag.offset; ++i) c--;//c for result length c=-1

                for (int i = 0; i < tag.length; ++i) {
                    result += result.charAt(c++);
                }
                result += tag.nextSymbol;
            }
            return result;
        }
    public static void main(String[] args) throws IOException {
       Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        LZ77 lz=new LZ77(15,10);
       /* String test ="<0,0,A>\n" +
                "<0,0,B>\n" +
                "<2,2,A>\n" +
                "<3,2,B>\n" +
                "<5,2,B>\n" +
                "<2,4,B>\n" +
                "<5,4,A>";

        ArrayList<LZ77.Tag> test1=new ArrayList<>();
        String[] test2 =test.split("\n");
        for (int i = 0; i < test2.length; i++) {
            LZ77.Tag temp;
            if (i>0)
                temp=new LZ77.Tag(test2[i]);
            else
                temp=new LZ77.Tag(test2[i]);

            test1.add(temp);
        }*/
        ArrayList<LZ77.Tag> tags= lz.encode(input);
        for (int i = 0; i < tags.size(); i++) {
            System.out.println( tags.get(i).toString());
        }


        //System.out.println( lz.decode(test1));
    }
}




