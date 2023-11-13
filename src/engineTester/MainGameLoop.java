package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConvertor.ModelData;
import objConvertor.OBJFileLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        ModelData data = OBJFileLoader.loadOBJ("tree");
        //RawModel model = OBJLoader.loadObjModel("tree",loader);
        RawModel treeModel= loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
        TexturedModel staticModel = new TexturedModel(treeModel,new ModelTexture(loader.textureLoader("tree")));

        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),new ModelTexture(loader.textureLoader("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.textureLoader("fern")));
        fern.getTexture().setHasTransparency(true);
        //ModelTexture texture = staticModel.getTexture();
//        texture.setShineDamper(10);
//        texture.setReflectivity(0.2f);

        //Entity entity= new Entity(staticModel, new Vector3f(0,0,0),0,0,0,1);
        Light light = new Light(new Vector3f(3000,2000,2000), new Vector3f(1,1,1));

//        Terrain terrain = new Terrain(-1,-1,loader, new ModelTexture(loader.textureLoader("grass")));
//        Terrain terrain2 = new Terrain(-1,0,loader, new ModelTexture(loader.textureLoader("grass")));
//        Terrain terrain3 = new Terrain(0,-1,loader, new ModelTexture(loader.textureLoader("grass")));
//        Terrain terrain4 = new Terrain(0,0,loader, new ModelTexture(loader.textureLoader("grass")));

        //++++++++TERRAIN TEXTURE STUFF++++++++++
        TerrainTexture backgroundTexture = new TerrainTexture(loader.textureLoader("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.textureLoader("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.textureLoader("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.textureLoader("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture);

        TerrainTexture blendMap = new TerrainTexture(loader.textureLoader("blendMap"));

        //+++++++++++++++++++++++++++++++++++++++

        List<Entity> entities = new ArrayList<>();
        Random random = new Random(676452);
        Terrain terrain = new Terrain(0,-1,loader, texturePack, blendMap,"heightMap");
        Terrain terrain2 = new Terrain(-1,-1,loader, texturePack, blendMap, "heightMap");

        for (int i = 0; i < 500; i++) {
            if (i%20 ==0){
                float x = random.nextFloat()*800-400;
                float z = random.nextFloat()*-600;
                float y = terrain.getHeightOfTerrain(x,z);
                entities.add(new Entity(fern, new Vector3f(x,y,z),0,random.nextFloat()*360,0,0.9f));
            }
            if (i%5 == 0) {
                float x = random.nextFloat()*800-400;
                float z = random.nextFloat()*-600;
                float y = terrain.getHeightOfTerrain(x,z);
                entities.add(new Entity(grass, new Vector3f(x,y,z),0,random.nextFloat()*360,0,1));
                x = random.nextFloat()*800-400;
                z = random.nextFloat()*-600;
                y = terrain.getHeightOfTerrain(x,z);
                entities.add(new Entity(staticModel, new Vector3f(x,y,z),0,random.nextFloat()*360,0,1));

            }
        }

        MasterRenderer renderer = new MasterRenderer();

//        ModelData bunnyModelP =  OBJFileLoader.loadOBJ("stanfordBunny");
//        //RawModel model = OBJLoader.loadObjModel("tree",loader);
//        RawModel bunnyModel = loader.loadToVAO(bunnyModelP.getVertices(), bunnyModelP.getTextureCoords(), bunnyModelP.getNormals(), bunnyModelP.getIndices());
//        TexturedModel stanfordBunny = new TexturedModel(bunnyModel,new ModelTexture(loader.textureLoader("white")));
        ModelData personB =  OBJFileLoader.loadOBJ("person");
        //RawModel model = OBJLoader.loadObjModel("tree",loader);
        RawModel personBlocky = loader.loadToVAO(personB.getVertices(), personB.getTextureCoords(), personB.getNormals(), personB.getIndices());
        TexturedModel stanfordBunny = new TexturedModel(personBlocky,new ModelTexture(loader.textureLoader("playerTexture")));
        stanfordBunny.getTexture().setUseFakeLighting(true);
        Player player = new Player(stanfordBunny, new Vector3f(100,0,-50),0,0,0,1);
        Camera camera = new Camera(player);
        while (!Display.isCloseRequested()){
            //game logic
            //entity.increaseRotation(0, 0.5f,0);

            //entity.increaseRotation(0,1,0);
            player.move(terrain);
            camera.move();
            renderer.processEntity(player);
            //render
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
//            renderer.processTerrain(terrain3);
//            renderer.processTerrain(terrain4);
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
