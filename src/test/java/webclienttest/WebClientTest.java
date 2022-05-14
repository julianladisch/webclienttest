package webclienttest;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.Timeout;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class WebClientTest {

  @Rule
  public Timeout timeoutRule = Timeout.seconds(10);

  @Test
  public void test(TestContext context) {
    Vertx vertx = Vertx.vertx();

    Router router = Router.router(vertx);
    router.route().handler(ctx -> {
      ctx.request().endHandler(x -> ctx.response().end("Hello"));
    });

    vertx.createHttpServer()
    .requestHandler(router)
    .listen(8000)
    .compose(x -> WebClient.create(vertx).requestAbs(HttpMethod.GET, "http://localhost:8000").send())
    .onComplete(context.asyncAssertSuccess());
  }
}

