package na.oasisUtils.ami;

class Base64 {
    private static final char PAD = '=';

    /**
     * Disables the default constructor.
     *  
     */
    private Base64() {
        ;
    }

    /**
     * Decodes the given string value.
     * 
     * @param value The string value.
     * @return The decoded value.
     */
    protected static String decode(String value) {
        if ((value != null) && (value.length() > 0)) {
            final int len = value.length();
            int padding = 0;

            for (int i = len - 1; value.charAt(i) == PAD; i--) {
                padding++;
            }

            final StringBuffer buff = new StringBuffer(((len * 6) >> 3)
                    - padding);

            int chunk;
            int j;
            int index = 0;

            for (int i = 0; i < len; i += 4) {
                chunk = (decode(value.charAt(i)) << 18)
                        + (decode(value.charAt(i + 1)) << 12)
                        + (decode(value.charAt(i + 2)) << 6)
                        + (decode(value.charAt(i + 3)));

                for (j = 0; (j < 3) && ((index + j) < buff.capacity()); j++) {
                    buff.append((char) ((chunk >> (8 * (2 - j))) & 0xff));
                }

                index += 3;
            }

            return buff.toString();
        }

        return "";
    }

    /**
     * Decodes the given integer.
     * 
     * @param i The integer to decode.
     * @return The decoded integer.
     */
    private static int decode(int i) {
        if ((i >= 'A') && (i <= 'Z')) {
            return (i - 'A');
        }

        if ((i >= 'a') && (i <= 'z')) {
            return ((26 + i) - 'a');
        }

        if ((i >= '0') && (i <= '9')) {
            return ((52 + i) - '0');
        }

        if (i == '+') {
            return 62;
        }

        return 63;
    }

    /**
     * Encodes the given integer.
     * 
     * @param i The integer to encode.
     * @return The encoded integer.
     */
    private static int encode(int i) {
        i &= 0x3f;

        if (i < 26) {
            return (i + 'A');
        } else if (i < 52) {
            return ((i + 'a') - 26);
        } else if (i < 62) {
            return ((i + '0') - 52);
        } else if (i == 62) {
            return '+';
        }

        return '/';
    }

    /**
     * Encodes the given string value.
     * 
     * @param value The string value.
     * @return The encoded value.
     */
    private static String encode(String value) {
        if ((value != null) && (value.length() > 0)) {
            final int len = value.length();
            int c;

            final StringBuffer buff = new StringBuffer(((len / 3) + 1) << 2);

            for (int i = 0; i < len; i++) {
                c = (value.charAt(i) >> 2);
                buff.append((char) encode(c));

                c = (value.charAt(i) << 4);

                if (++i < len) {
                    c |= (value.charAt(i) >> 4);
                }

                buff.append((char) encode(c));

                if (i < len) {
                    c = (value.charAt(i) << 2);

                    if (++i < len) {
                        c |= (value.charAt(i) >> 6);
                    }

                    buff.append((char) encode(c));
                } else {
                    i++;
                    buff.append(PAD);
                }

                if (i < len) {
                    c = value.charAt(i);
                    buff.append((char) encode(c));
                } else {
                    buff.append(PAD);
                }
            }

            return (buff.toString());
        }

        return "";
    }
}