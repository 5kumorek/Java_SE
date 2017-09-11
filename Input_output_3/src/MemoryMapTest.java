import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.Scanner;
import java.util.zip.CRC32;

public class MemoryMapTest {
	public static void main(String[] args) throws IOException
	{
		System.out.println("Podaj nazwę pliku, dla którego będzie sprawdzana suma kontrolna. ");
		//Scanner scan = new Scanner(System.in);
		//D:/Java2/JAT_practice1/text5.txt
		//String name = scan.nextLine();
		String name = "text5.txt";
		Path filepath = Paths.get(name);
		
		System.out.println("InputStream: ");
		long time = System.currentTimeMillis();
		long crcValue = checksumInputStream(filepath);
		long end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end-time)+" miliseconds");
		
		System.out.println("BufferedStream: ");
		time = System.currentTimeMillis();
		crcValue = checksumBufferedStream(filepath);
		end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end-time)+" miliseconds");
		
		System.out.println("RandomAccessFile: ");
		time = System.currentTimeMillis();
		crcValue = checksumRandomAccessFile(filepath);
		end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end-time)+" miliseconds");
		
		System.out.println("MappedFile: ");
		time = System.currentTimeMillis();
		crcValue = checksumMappedFile(filepath);
		end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end-time)+" miliseconds");
		
		
	}
	public static long checksumInputStream(Path path) throws IOException
	{
		try(InputStream in = Files.newInputStream(path))
		{
			CRC32 crc = new CRC32();
			int c;
			while((c=in.read())!=-1)
				crc.update(c);
		return crc.getValue();
		}
	}
	public static long checksumBufferedStream(Path path) throws IOException
	{
		try(InputStream in = new BufferedInputStream(Files.newInputStream(path)))
		{
			CRC32 crc = new CRC32();
			int c;
			while((c=in.read())!=-1)
				crc.update(c);
			return crc.getValue();
		}
	}
	public static long checksumRandomAccessFile(Path path) throws IOException
	{
		try(RandomAccessFile file = new RandomAccessFile(path.toFile(), "r"))
		{
			long length = file.length();
			CRC32 crc = new CRC32();
			int c;
			for(long p=0;p<length;p++)
			{
				file.seek(p);
				c = file.readByte();
				crc.update(c);
			}
			return crc.getValue();
		}
	}
	public static long checksumMappedFile(Path path) throws IOException
	{
		try(FileChannel channel = FileChannel.open(path))
		{
			CRC32 crc = new CRC32();
			int length = (int) channel.size();
			MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);
			
			for(int i=0;i<length;i++)
			{
				int c = buffer.get(i);
				crc.update(c);
			}
			return crc.getValue();
		}
	}
}
