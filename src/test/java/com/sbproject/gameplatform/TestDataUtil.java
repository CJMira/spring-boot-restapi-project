package com.sbproject.gameplatform;


import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import com.sbproject.gameplatform.domain.entities.GameEntity;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static CompanyEntity createTestCompanyA(){
        return CompanyEntity.builder()
                .id(1L)
                .name("Mambo")
                .yearFounded(1980)
                .build();
    }

    public static CompanyEntity createTestCompanyB(){
        return CompanyEntity.builder()
                .id(2L)
                .name("FameStation")
                .yearFounded(1972)
                .build();
    }

    public static CompanyEntity createTestCompanyC(){
        return CompanyEntity.builder()
                .id(3L)
                .name("VPlane")
                .yearFounded(1986)
                .build();
    }

    public static GameEntity createTestGameA(final CompanyEntity companyEntity){
        return GameEntity.builder()
                .id(1L)
                .title("Dungeonmania")
                .company(companyEntity)
                .build();
    }

    public static GameEntity createTestGameB(final CompanyEntity companyEntity){
        return GameEntity.builder()
                .id(2L)
                .title("War of Cards")
                .company(companyEntity)
                .build();
    }

    public static GameEntity createTestGameC(final CompanyEntity companyEntity){
        return GameEntity.builder()
                .id(3L)
                .title("Dig n' Shine")
                .company(companyEntity)
                .build();
    }
}
