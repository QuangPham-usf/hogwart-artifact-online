package edu.usf.cs.hogwart_artifact_online.User;

import edu.usf.cs.hogwart_artifact_online.User.UserDto.UserDto;
import edu.usf.cs.hogwart_artifact_online.artifact.Artifact;
import edu.usf.cs.hogwart_artifact_online.artifact.ArtifactNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
// remember to check null at "delete logic"
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

        private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public Users findById(Integer id) {
        Users user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user;
    }

    public List<Users> findAll() {
        List<Users> ans = userRepo.findAll();
        return ans;
    }

    public Users save(Users users)
    {
        //Encode RAW & WEAK password before saving
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return userRepo.save(users);
    }

    public Users update(Integer Id, Users update){
        Users oldOne = this.userRepo.findById(Id).orElseThrow(() -> new UserNotFoundException(Id));
        oldOne.setUserName(update.getUserName());
        oldOne.setRoles(update.getRoles());
        oldOne.setEnabled(update.getEnabled());
        Users newOne = userRepo.save(oldOne);// if there is an ID,save() will just modify else add()

        return newOne;
    }

    public void delete(Integer Id) {
        this.userRepo.findById(Id).orElseThrow(() -> new UserNotFoundException(Id));

        userRepo.deleteById(Id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//UsernameNotFoundException available, standard
        return userRepo.findByUserName(username) // map use for transformation form type to type
                .map(users -> new MyUserPrincipal(users))// Name object is users, put it into MyUserPrincipal constructor,
                .orElseThrow(() -> new UsernameNotFoundException("not found " + username));
    }
}
/*
1.   @FunctionalInterface
public interface name_here<T, R> { take t as input, R as output
    R apply(T t);
    public interface Supplier<T> // no
public class UserToPrincipalConverter implements Function<Users, MyUserPrincipal> {

    @Override
    public MyUserPrincipal apply(Users user) { // belong to orElseThrow
        return new MyUserPrincipal(user);
    }

.map(...)	Optional<T>	KHÔNG (Chỉ đổi ruột)
.filter(...)	Optional<T>	KHÔNG (Lọc bớt ruột)
.get()	T (Dữ liệu thô)	CÓ (Nhưng nguy hiểm nếu hộp rỗng)
.orElse(...)	T (Dữ liệu thô)	CÓ (An toàn, có giá trị dự phòng)
.orElseThrow(...)	T (Dữ liệu thô)	CÓ (An toàn, ném lỗi nếu rỗng)


 */