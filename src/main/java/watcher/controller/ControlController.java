package watcher.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import watcher.utils.OperatorServer;
import watcher.vo.Result;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/control")
public class ControlController {


    @Autowired
    private OperatorServer os;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/cmd",method = RequestMethod.POST)
    public String cmdop(@RequestBody HashMap<String,String> map){
        try {
            os.out(map.get("cmd"));
            return JSON.toJSONString(new Result(200,"成功",null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(new Result(300,"无法接入",null));
    }


    @RequestMapping(value = "/cmdres",method = RequestMethod.GET)
    public String cmdres(){
        String res = (String)redisTemplate.opsForList().rightPop("result");
        return JSON.toJSONString(new Result(200,"成功",res));
    }

    @RequestMapping(value = "/saveStaticInfo",method = RequestMethod.POST)
    public void saveSInfo(@RequestBody Map map){
        redisTemplate.opsForValue().set("StaticInfo",JSON.toJSONString(map));
    }
    @RequestMapping(value = "/saveDynamicInfo",method = RequestMethod.POST)
    public void saveDInfo(@RequestBody Map map){
        redisTemplate.opsForValue().set("DynamicInfo",JSON.toJSONString(map));
    }
    @RequestMapping(value = "/getStaticInfo",method = RequestMethod.GET)
    public String getSInfo(){
        return JSON.toJSONString(new Result(200,"成功",redisTemplate.opsForValue().get("StaticInfo")));
    }
    @RequestMapping(value = "/getDynamicInfo",method = RequestMethod.GET)
    public String getDInfo(){
        return JSON.toJSONString(new Result(200,"成功",redisTemplate.opsForValue().get("DynamicInfo")));
    }
}
