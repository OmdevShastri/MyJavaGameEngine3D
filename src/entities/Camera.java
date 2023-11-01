package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private float distanceFromPlayer =50;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0,4,0);
    private float pitch = 20;
    private float yaw= 180;
    private float roll;
    private Player player;

//    public Camera(){}
    public Camera(Player player){
        this.player = player;
    }

    public void move(){
//        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
//            if (Keyboard.isKeyDown(Keyboard.KEY_S)){
//                position.z-=0.5f;
//            }
//            if (Keyboard.isKeyDown(Keyboard.KEY_W)){
//                position.z+=0.5f;
//            }
//            if (Keyboard.isKeyDown(Keyboard.KEY_A)){
//                position.x+=0.5f;
//            }
//            if (Keyboard.isKeyDown(Keyboard.KEY_D)){
//                position.x-=0.5f;
//            }
//            if (Keyboard.isKeyDown(Keyboard.KEY_R)){
//                position.y+=0.5f;
//            }
//            if (Keyboard.isKeyDown(Keyboard.KEY_F)){
//                position.y-=0.5f;
//            }
//            if (Keyboard.isKeyDown(Keyboard.KEY_E)){
//                yaw-=1.0f;
//            }if (Keyboard.isKeyDown(Keyboard.KEY_Q)){
//                yaw+=1.0f;
//            }
//        }else {
//
//        }

        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizontalDist = calculateHorizontalDistance();
        float verticalDist = calculateVerticalDistance();
        calculateCameraPosition(horizontalDist,verticalDist);
        this.yaw = 180 - (player.getRotY()+angleAroundPlayer);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private void calculateCameraPosition(float horizD, float vertD){
        float theta = player.getRotY()+angleAroundPlayer;
        float offsetX = (float) (horizD*Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizD*Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x -offsetX;
        position.z = player.getPosition().z -offsetZ;
        position.y = player.getPosition().y+vertD;
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromPlayer* Math.cos(Math.toRadians(pitch)));
    }
    private float calculateVerticalDistance(){
        return (float) (distanceFromPlayer* Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom(){
        float zoomLevel = Mouse.getDWheel()*0.1f;
        distanceFromPlayer -=zoomLevel;
    }
    private void calculatePitch(){
        if (Mouse.isButtonDown(1)){
            float pitchChange = Mouse.getDY() *  0.1f;
            pitch -= pitchChange;
        }
    }
    private void calculateAngleAroundPlayer(){
        if (Mouse.isButtonDown(0)){
            float angleChange = Mouse.getDX()*0.3f;
            angleAroundPlayer -= angleChange;
        }
    }
}
