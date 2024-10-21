//package com.meysam.backoffice.repository;
//
//import com.ada.bourse.admin.oms.repositories.entities.CompanyAlias;
//import com.ada.bourse.admin.oms.repositories.entities.Instrument;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//@Repository
//public class CompanyAliasRepository extends BaseJDBCRepository<CompanyAlias> {
//
//
//    public CompanyAliasRepository(DataSource dataSource) {
//        super(dataSource);
//    }
//
//    @Override
//    protected CompanyAlias mapResultSetToEntity(ResultSet rs) throws SQLException {
//        return CompanyAlias.builder()
//                .id(rs.getInt("id"))
//                .code(rs.getString("code"))
//                .companyId(rs.getInt("ida"))
//                .build();
//    }
//
//    public Instrument getById(long id) {
//        //TODO
//        return new Instrument();
//    }
//
//}
