package com.telefonica.movistar.planfs.configurations;

import java.nio.charset.Charset;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

@Component
public class Utility {
	private static final Charset ISO_CHARSET = Charset.forName("ISO-8859-1");

	public static void main(String[] args) throws Exception {
		try {
			String message = "helló world";
			System.out.println("Mensaje - " + message);
			String encode = encodeBase64(message);
			System.out.println("Codificado en base 64 - " + encode);
			encode = "fCBQcnVlYmFzIHx8IEFQUCB8IGlkZW50aWZpY2FkbyBjb24gfCBDQyB8fCAxOTE1MDc3OCB8IHkgdXN1YXJpbyB8IHBydWViYXNhcHB2MUBnbWFpbC5jb20gfCBpbmljaW8gc2VzafNuIGNvbiBsYSBs7W5lYSB8IDMxODY0OTcxNzEgfCBkZXNkZSBNaSBNb3Zpc3RhciBhIHRyYXbpcyBkZSBsYSBhcGxpY2FjafNuIEFQUE1PVklMIGVsIHwgMTAvMDQvMjAxNyB8fCAxODoxMzoxOSB8Lg==";
			System.out.println("Decodificado en base 64 - " + decodeBase64(encode));
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			e.printStackTrace();
		}
	}

	public static String getKey(String user) {
		String key = "";
		try {
			int j = 1;
			int total = 0;
			String resultado = "";
			for (int i = 0; i < user.length(); i++) {
				total = Integer.parseInt(user.substring(i, i + 1)) + j;
				resultado = (new StringBuilder(String.valueOf(total))).toString();
				resultado = resultado.substring(resultado.length() - 1, resultado.length());
				if (j < 6) {
					j++;
				} else {
					j = 1;
				}
				key = String.valueOf(key) + resultado;
			}
		} catch (Exception e) {
			key = "";
		}
		return key;
	}

	public static String decodeBase64(String value) {
		String decode = "";
		try {
			byte[] decoded = DatatypeConverter.parseBase64Binary(value);
			decode = new String(decoded, ISO_CHARSET);
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			e.printStackTrace();
		}
		return decode;
	}

	public static String encodeBase64(String value) {
		byte[] message = value.getBytes(ISO_CHARSET);
		String encoded = "";
		try {
			encoded = DatatypeConverter.printBase64Binary(message);
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			e.printStackTrace();
		}
		return encoded;
	}
}
