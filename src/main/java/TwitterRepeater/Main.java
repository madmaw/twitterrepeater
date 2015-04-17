package TwitterRepeater;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.thoughtworks.xstream.XStream;
import org.apache.batik.bridge.*;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGOMSVGElement;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.imageio.ImageIO;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * Created by Chris on 4/04/2015.
 */
public class Main {

    public static final String ACCESS_TOKEN_PATH = "access.token";

    public static final void main(String[] args) {

        Twitter twitter = TwitterFactory.getSingleton();

        String privateKey = args[0];
        String publicKey = args[1];

        twitter.setOAuthConsumer(privateKey, publicKey);
        try {
            RequestToken requestToken = twitter.getOAuthRequestToken();
            AccessToken accessToken = loadAccessToken();

            if( accessToken != null ) {
                twitter.setOAuthAccessToken(accessToken);
            } else {
                accessToken = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (null == accessToken)
                {
                    System.out.println("Open the following URL and grant access to your account:");
                    System.out.println(requestToken.getAuthorizationURL());
                    System.out.print("Enter the PIN(if available) or just hit enter.[PIN]:");
                    String pin = br.readLine();
                    try
                    {
                        if (pin.length() > 0)
                        {
                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                        } else
                        {
                            accessToken = twitter.getOAuthAccessToken();
                        }
                    } catch (TwitterException te) {
                        if (401 == te.getStatusCode())
                        {
                            System.out.println("Unable to get the access token.");
                        } else
                        {
                            te.printStackTrace();
                        }
                    }
                }

                storeAccessToken(accessToken);
            }

            User user = twitter.verifyCredentials();

            System.out.println("your name is "+user.getName());
            System.out.println("your screen name is @" + user.getScreenName());
            System.out.println("your user id is " + user.getId());



            IDs followerIDs = twitter.friendsFollowers().getFollowersIDs(-1);
            do {
                long ids[] = followerIDs.getIDs();
                for( long id : ids ) {
                    User follower = twitter.users().showUser(id);
                    System.out.println("followed by @" + follower.getScreenName());
                }
            } while( followerIDs.hasNext() );

            TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
            twitterStream.setOAuthConsumer(privateKey, publicKey);
            twitterStream.setOAuthAccessToken(accessToken);
            twitterStream.addListener(new UserStreamListener() {
                @Override
                public void onDeletionNotice(long l, long l1) {

                }

                @Override
                public void onFriendList(long[] longs) {

                }

                @Override
                public void onFavorite(User user, User user1, Status status) {
                    System.out.println("@" + user.getScreenName()+" favorited "+status.getText());
                }

                @Override
                public void onUnfavorite(User user, User user1, Status status) {
                    System.out.println("@" + user.getScreenName()+" unfavorited "+status.getText());

                }

                @Override
                public void onFollow(User user, User user1) {
                    System.out.println("@" + user.getScreenName() + " followed @" + user1.getScreenName());
                }

                @Override
                public void onUnfollow(User user, User user1) {
                    System.out.println("@" + user.getScreenName() + " unfollowed @" + user1.getScreenName());
                }

                @Override
                public void onDirectMessage(DirectMessage directMessage) {

                }

                @Override
                public void onUserListMemberAddition(User user, User user1, UserList userList) {

                }

                @Override
                public void onUserListMemberDeletion(User user, User user1, UserList userList) {

                }

                @Override
                public void onUserListSubscription(User user, User user1, UserList userList) {

                }

                @Override
                public void onUserListUnsubscription(User user, User user1, UserList userList) {

                }

                @Override
                public void onUserListCreation(User user, UserList userList) {

                }

                @Override
                public void onUserListUpdate(User user, UserList userList) {

                }

                @Override
                public void onUserListDeletion(User user, UserList userList) {

                }

                @Override
                public void onUserProfileUpdate(User user) {

                }

                @Override
                public void onUserSuspension(long l) {

                }

                @Override
                public void onUserDeletion(long l) {

                }

                @Override
                public void onBlock(User user, User user1) {

                }

                @Override
                public void onUnblock(User user, User user1) {

                }

                @Override
                public void onStatus(Status status) {
                    if( status.getUser().getId() != user.getId() ) {
                        // do not respond to our own tweets!!

                        XStream xstream = new XStream();
                        String xml = xstream.toXML(status);
                        System.out.println(xml);
                        try {
                            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
                            encoder.setRepeat(0);
                            Color background = Color.black;
                            int width = 506;
                            int height = 253;
                            encoder.setSize(width, height);
                            ByteArrayOutputStream gifOutputStream = new ByteArrayOutputStream();
                            encoder.start(gifOutputStream);

                            encoder.setDispose(1);
                            addFrames(encoder, "/background.svg.xsl", xml, width, height, new float[]{0}, null);
                            encoder.setDispose(3);
                            addFrames(
                                    encoder,
                                    "/status.svg.xsl",
                                    xml,
                                    width,
                                    height,
                                    new float[]{
                                            0.05f,
                                            0.1f,
                                            0.15f,
                                            0.2f,
                                            0.25f,
                                            0.3f,
                                            0.35f,
                                            0.4f,
                                            0.45f,
                                            0.5f,
                                            0.55f,
                                            0.6f,
                                            0.65f,
                                            0.7f,
                                            0.75f,
                                            0.8f,
                                            0.85f,
                                            0.9f,
                                            0.95f,
                                            1,
                                            120,
                                            240,
                                            360
                                    }, background);

                            encoder.finish();

                            byte[] gif = gifOutputStream.toByteArray();
                            StatusUpdate statusUpdate = new StatusUpdate("@"+status.getUser().getScreenName());
                            statusUpdate.setInReplyToStatusId(status.getId());

                            statusUpdate.setMedia("gif", new ByteArrayInputStream(gif));

                            FileOutputStream fos = new FileOutputStream("out.gif");
                            fos.write(gif);
                            fos.close();

                            twitter.updateStatus(statusUpdate);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }

                }

                @Override
                public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

                }

                @Override
                public void onTrackLimitationNotice(int i) {

                }

                @Override
                public void onScrubGeo(long l, long l1) {

                }

                @Override
                public void onStallWarning(StallWarning stallWarning) {

                }

                @Override
                public void onException(Exception e) {
                    e.printStackTrace();
                }
            });

            twitterStream.user();

        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    private static void storeAccessToken(AccessToken accessToken) throws IOException {
        File file = new File(ACCESS_TOKEN_PATH);
        FileOutputStream fos = new FileOutputStream(file);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(accessToken);
        } finally {
            fos.close();
        }
    }

    private static AccessToken loadAccessToken() throws Exception {
        File file = new File(ACCESS_TOKEN_PATH);
        AccessToken result;
        if( file.exists() ) {
            FileInputStream fis = new FileInputStream(file);
            try {
                ObjectInputStream ois = new ObjectInputStream(fis);
                result = (AccessToken)ois.readObject();
            } finally {
                fis.close();
            }

        } else {
            result = null;
        }
        return result;
    }


    private static void addFrames(AnimatedGifEncoder encoder, String xslPath, String xml, int width, int height, float animationTimes[], Color background) throws Exception {
        StreamSource xslSource = new StreamSource(Main.class.getResourceAsStream(xslPath));
        StreamSource xmlSource = new StreamSource(new StringReader(xml));
        StringWriter svgWriter = new StringWriter();
        StreamResult svgResult = new StreamResult(svgWriter);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(xslSource);
        transformer.setParameter("width", width);
        transformer.setParameter("height", height);
        transformer.transform(xmlSource, svgResult);
        String svg = svgWriter.toString();

        System.out.println(svg);

        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        String docBaseUri = Main.class.getResource(xslPath).toExternalForm();
        SVGDocument svgDocument = f.createSVGDocument(docBaseUri, new StringReader(svg));

        UserAgentAdapter userAgent = new UserAgentAdapter();
        DocumentLoader loader = new DocumentLoader(userAgent);
        GVTBuilder builder = new GVTBuilder();
        BridgeContext ctx = new BridgeContext(userAgent, loader);
        ctx.setDynamicState(BridgeContext.DYNAMIC);
        GraphicsNode rootGN = builder.build(ctx, svgDocument);

        //SVGOMSVGElement element = (SVGOMSVGElement)svgDocument.getDocumentElement();
        //rootGN.setRenderingHints(renderingHints);

        float previousAnimationTime = 0;

        for( float animationTime : animationTimes ) {
            float animationDuration = animationTime - previousAnimationTime;
            SVGAnimationEngine animationEngine = ctx.getAnimationEngine();
            if (!animationEngine.hasStarted()) {
                animationEngine.start(System.currentTimeMillis());
            }
            if (!animationEngine.isPaused()) {
                animationEngine.pause();
            }
            animationEngine.setCurrentTime(animationTime);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = GraphicsUtil.createGraphics(image);
            if( background != null ) {
                g.setBackground(background);
                g.clearRect(0, 0, width, height);
            }
            //g.addRenderingHints(renderingHints);
            rootGN.paint(g);
            g.dispose();

            int delay = (int)(animationDuration * 1000);
            encoder.setDelay(delay);
            encoder.setTransparent(background);
            encoder.addFrame(image);
            previousAnimationTime = animationTime;
        }
    }
}
