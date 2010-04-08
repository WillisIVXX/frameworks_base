/*
 * Copyright (C) 2009 The Android Open Source Project
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
package android.pim.vcard;

import android.pim.vcard.exception.VCardException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * </p>
 * vCard parser implementation mostly for vCard 2.1. See the specification for more detail
 * about the spec itself.
 * </p>
 * <p>
 * The spec is written in 1996, and currently various types of "vCard 2.1" exist.
 * To handle real the world vCard formats appropriately and effectively, this class does not
 * obey with strict vCard 2.1. In stead, not only vCard spec but also real world
 * vCard is considered.
 * </p>
 * e.g. A lot of devices and softwares let vCard importer/exporter to use
 * the PNG format to determine the type of image, while it is not allowed in
 * the original specification. As of 2010, we can see even the FLV format
 * (possible in Japanese mobile phones).
 * </p>
 */
public final class VCardParser_V21 implements VCardParser {
    /**
     * A unmodifiable Set storing the property names available in the vCard 2.1 specification.
     */
    public static final Set<String> sKnownPropertyNameSet =
            Collections.unmodifiableSet(new HashSet<String>(
                    Arrays.asList("BEGIN", "LOGO", "PHOTO", "LABEL", "FN", "TITLE", "SOUND",
                            "VERSION", "TEL", "EMAIL", "TZ", "GEO", "NOTE", "URL",
                            "BDAY", "ROLE", "REV", "UID", "KEY", "MAILER")));

    /**
     * A unmodifiable Set storing the types known in vCard 2.1.
     */
    public static final Set<String> sKnownTypeSet =
            Collections.unmodifiableSet(new HashSet<String>(
                    Arrays.asList("DOM", "INTL", "POSTAL", "PARCEL", "HOME", "WORK",
                            "PREF", "VOICE", "FAX", "MSG", "CELL", "PAGER", "BBS",
                            "MODEM", "CAR", "ISDN", "VIDEO", "AOL", "APPLELINK",
                            "ATTMAIL", "CIS", "EWORLD", "INTERNET", "IBMMAIL",
                            "MCIMAIL", "POWERSHARE", "PRODIGY", "TLX", "X400", "GIF",
                            "CGM", "WMF", "BMP", "MET", "PMB", "DIB", "PICT", "TIFF",
                            "PDF", "PS", "JPEG", "QTIME", "MPEG", "MPEG2", "AVI",
                            "WAVE", "AIFF", "PCM", "X509", "PGP")));

    /**
     * A unmodifiable Set storing the values available in the vCard 2.1 specification.
     */
    public static final Set<String> sKnownValueSet =
            Collections.unmodifiableSet(new HashSet<String>(
                    Arrays.asList("INLINE", "URL", "CONTENT-ID", "CID")));

    /**
     * Though vCard 2.1 specification does not allow "B" encoding, some data may have it.
     * We allow it for safety...
     */
    // TODO: move B to another something and make this member public
    /* package */ static final HashSet<String> sAvailableEncoding =
        new HashSet<String>(Arrays.asList(
                "7BIT", "8BIT", "QUOTED-PRINTABLE", "BASE64", "B"));

    private final VCardParserImpl_V21 mVCardParserImpl;

    public VCardParser_V21() {
        mVCardParserImpl = new VCardParserImpl_V21();
    }

    public VCardParser_V21(VCardSourceDetector detector) {
        mVCardParserImpl = new VCardParserImpl_V21(detector);
    }

    public VCardParser_V21(int parseType) {
        mVCardParserImpl = new VCardParserImpl_V21(parseType);
    }

    //// Implemented methods 

    public boolean parse(InputStream is, VCardInterpreter interepreter)
            throws IOException, VCardException {
        return mVCardParserImpl.parse(is, VCardConfig.DEFAULT_TEMPORARY_CHARSET, interepreter);
    }

    public boolean parse(InputStream is, String charset, VCardInterpreter interpreter)
            throws IOException, VCardException {
        return mVCardParserImpl.parse(is, charset, interpreter);
    }

    public boolean parse(InputStream is, String charset,
            VCardInterpreter interpreter, boolean canceled)
            throws IOException, VCardException {
        return mVCardParserImpl.parse(is, charset, interpreter, canceled);
    }

    public void cancel() {
        mVCardParserImpl.cancel();
    }
}
