package oauth2Web.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    //아이디 검색(인덱스서비스2번)
    Optional<MemberEntity> findBymid(String mid);

}
