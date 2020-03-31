package watcher.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/shot")
public class ScreenShotController {

    @Autowired
    private RedisTemplate redisTemplate;

    private String predata = "data:image/jpg;base64,";

    @GetMapping("/get")
    public String getScreenShot(){
        String currtData = "data:image/jpg;base64,"+ redisTemplate.opsForList().rightPop("frame");
        if(currtData.equals("data:image/jpg;base64,")){
            return predata;
        }
        predata =  currtData;
        return currtData;
    }

}
