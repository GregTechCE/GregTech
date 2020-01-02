package gregtech.api.render.scene;

@FunctionalInterface
public interface SceneRenderCallback {

    void preRenderScene(WorldSceneRenderer renderer);

}
