package uz.itcenter.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckerService {
    private UserService userService;
    private CenterService centerService;
    private RegionsService regionsService;

    public CheckerService() {}
}
