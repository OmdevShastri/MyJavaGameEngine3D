package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjModel("tree",loader);

        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.textureLoader("tree")));
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),new ModelTexture(loader.textureLoader("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.textureLoader("fern")));
        fern.getTexture().setHasTransparency(true);
        //ModelTexture texture = staticModel.getTexture();
//        texture.setShineDamper(10);
//        texture.setReflectivity(0.2f);

        //Entity entity = new Entity(staticModel, new Vector3f(0,0,0),0,0,0,1);
        Light light = new Light(new Vector3f(3000,2000,2000), new Vector3f(1,1,1));

        Terrain terrain = new Terrain(-1,-1,loader, new ModelTexture(loader.textureLoader("grass")));
        Terrain terrain2 = new Terrain(0,-1,loader, new ModelTexture(loader.textureLoader("grass")));

        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800-400,0,random.nextFloat()*-600),0,0,0,1));
            entities.add(new Entity(grass, new Vector3f(random.nextFloat()*800-400,0,random.nextFloat()*-600),0,0,0,1));
            entities.add(new Entity(fern, new Vector3f(random.nextFloat()*800-400,0,random.nextFloat()*-600),0,0,0,1));
        }

        Camera camera = new Camera();
        MasterRenderer renderer = new MasterRenderer();

        while (!Display.isCloseRequested()){
            //game logic
            //entity.increaseRotation(0, 0.5f,0);
            camera.move();
            //entity.increaseRotation(0,1,0);

            //renderer.prepare();
            //render
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            for (Entity e :
                    entities) {
                renderer.processEntity(e);
            }

            renderer.render(light,camera);
            DisplayManager.updateDisplay();

        }
        //shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
