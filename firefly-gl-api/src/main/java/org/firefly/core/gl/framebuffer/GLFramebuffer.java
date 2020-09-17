package org.firefly.core.gl.framebuffer;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

public class GLFramebuffer {

    private int id;

    public GLFramebuffer() {
        id = glGenFramebuffers();
    }

    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void setDrawBuffer(int i){
        glDrawBuffer(GL_COLOR_ATTACHMENT0 + i);
    }

    public void setDrawBuffers(IntBuffer buffer){
        glDrawBuffers(buffer);
    }

    public void createColorBufferAttachment(int x, int y, int i, int internalFormat){
        int colorBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, colorBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, internalFormat, x, y);
        glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + i, GL_RENDERBUFFER, colorBuffer);
    }

    public void createColorTextureAttachment(int texture, int i){
        glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + i, GL_TEXTURE_2D, texture, 0);
    }

    public void createColorTextureAttachment(int texture, int i, boolean isMultisample){
        glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + i,
                isMultisample ? GL_TEXTURE_2D_MULTISAMPLE : GL_TEXTURE_2D, texture, 0);
    }

    public void createDepthBufferAttachment(int x, int y){
        int depthBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32F, x, y);
        glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
    }

    public void createDepthTextureAttachment(int texture){
        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, texture, 0);
    }

    public void createColorTextureMultisampleAttachment(int texture, int i){
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + i,
                GL_TEXTURE_2D_MULTISAMPLE, texture, 0);
    }

    public void createDepthTextureMultisampleAttachment(int texture){
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D_MULTISAMPLE, texture, 0 );
    }

    public void createDepthTextureAttachment(int texture, boolean isMultisample){
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, isMultisample ? GL_TEXTURE_2D_MULTISAMPLE : GL_TEXTURE_2D, texture, 0 );
    }

    public void createColorBufferMultisampleAttachment(int samples, int attachment, int width, int height, int internalformat){
        int colorBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, colorBuffer);
        glRenderbufferStorageMultisample(GL_RENDERBUFFER, samples, internalformat, width, height);
        glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER,GL_COLOR_ATTACHMENT0 + attachment,
                GL_RENDERBUFFER,colorBuffer);
    }

    public void createDepthBufferMultisampleAttachment(int samples, int width, int height){
        int depthBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        glRenderbufferStorageMultisample(GL_RENDERBUFFER, samples, GL_DEPTH_COMPONENT32F, width, height);
        glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER,GL_DEPTH_ATTACHMENT,
                GL_RENDERBUFFER,depthBuffer);
    }

    public void blitFramebuffer(int sourceAttachment, int destinationAttachment, int writeFBO, int width, int height){
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, writeFBO);
        glBindFramebuffer(GL_READ_FRAMEBUFFER, id);
        glReadBuffer(GL_COLOR_ATTACHMENT0 + sourceAttachment);
        glDrawBuffer(GL_COLOR_ATTACHMENT0 + destinationAttachment);
        glBlitFramebuffer(0, 0, width, height, 0, 0, width, height,
                GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT, GL_NEAREST);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
    }

    public void checkStatus()
    {
        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE){
            return;
        }
        else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_UNDEFINED){
            System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_UNDEFINED error");
            System.exit(1);
        }
        else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT){
            System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT error");
            System.exit(1);
        }
        else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT){
            System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT error");
            System.exit(1);
        }
        else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER){
            System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER error");
            System.exit(1);
        }
        else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER){
            System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER error");
            System.exit(1);
        }
        else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_UNSUPPORTED){
            System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_UNSUPPORTED error");
            System.exit(1);
        }
        else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE){
            System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE error");
            System.exit(1);
        }
        else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS){
            System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS error");
            System.exit(1);
        }
    }

    public int getId() {
        return id;
    }
}
