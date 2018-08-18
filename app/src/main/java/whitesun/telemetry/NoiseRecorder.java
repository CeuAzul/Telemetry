package whitesun.telemetry;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.io.DataOutputStream;
import java.io.IOException;

public class NoiseRecorder {
    private static int[] mSampleRates = new int[] { 8000, 11025, 22050, 44100 };


    short[] buffer; // buffer where we will put captured samples
    DataOutputStream output; // output stream to target file
    private MediaRecorder recorder;

    public double getNoiseLevel() {

        return recorder.getMaxAmplitude();
    }

    public void inicializeSom() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile("/dev/null");

        try {
            recorder.prepare();
        } catch (IOException e) {
            System.err.println(e);
        }

        // After prepare
        recorder.start();
    }


    public void finalizaSom() {
        recorder.stop();
        recorder.release();
    }

    public AudioRecord findAudioRecord() {
        for (int rate : mSampleRates) {
            for (short audioFormat : new short[] { AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT }) {
                for (short channelConfig : new short[] { AudioFormat.CHANNEL_IN_MONO, AudioFormat.CHANNEL_IN_STEREO }) {
                    try {
                        int bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);

                        if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                            // check if we can instantiate and have a success
                            AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, rate, channelConfig, audioFormat, bufferSize);

                            if (recorder.getState() == AudioRecord.STATE_INITIALIZED) {
                                return recorder;
                            }
                        }
                    } catch (Exception e) {
                        // Do nothing
                    }
                }
            }
        }

        return null;
    }

}
