package edu.usf.cs.hogwart_artifact_online.User;

import edu.usf.cs.hogwart_artifact_online.User.UserDto.UserDto;
import edu.usf.cs.hogwart_artifact_online.artifact.Artifact;
import edu.usf.cs.hogwart_artifact_online.artifact.ArtifactNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
// remember to check null at "delete logic"
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Users findById(Integer id) {
        Users user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user;
    }

    public List<Users> findAll() {
        List<Users> ans = userRepo.findAll();
        return ans;
    }

    public Users save(Users users) {
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
}
