package com.nengdong.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

import static com.nengdong.utils.JwtUtils.APP_SECRET;

public class test {
    public static void main(String[] args) {
        String jwtToken = JwtUtils.getJwtToken("1", "123");
        System.out.println(jwtToken);

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        System.out.println((String)claims.get("id"));
        System.out.println((String)claims.get("nickname"));

        System.out.println("--------------");


        String jwtTokenultra = JwtUtils.getJwtTokenUltra("1", "123","teacher");
        System.out.println(jwtTokenultra);

        Jws<Claims> claimsJwsultra = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtTokenultra);
        Claims claimsultra = claimsJwsultra.getBody();
        System.out.println((String)claimsultra.get("id"));
        System.out.println((String)claimsultra.get("nickname"));
        System.out.println((String)claimsultra.get("type"));


    }
}
