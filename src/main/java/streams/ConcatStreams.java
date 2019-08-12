package streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Stream;

public class ConcatStreams {
	
	public void collectAllTheWordsFrom4Files() throws IOException {
		try(Stream<String> strm1 = Files.lines(Paths.get("src/main/resources/concatStreams/TO16C9~1.TXT"));
				Stream<String> strm2 = Files.lines(Paths.get("src/main/resources/concatStreams/TOMSAW~2.TXT"));
				Stream<String> strm3 = Files.lines(Paths.get("src/main/resources/concatStreams/TOMSAW~3.TXT"));
				Stream<String> strm4 = Files.lines(Paths.get("src/main/resources/concatStreams/TOMSAW~4.TXT"));
				){
			Stream<Stream<String>> streams = Stream.of(strm1, strm2, strm3, strm4);
			//System.out.println("# Streams : "+streams.count());
			
			Stream<String> streamOfLines = streams.flatMap(Function.identity());
			//System.out.println("# Streams : "+streamOfLines.count());
			
			long wordCount = streamOfLines.flatMap(line -> Stream.of(line.split(" ")))
					.sorted()
					.filter(word -> word.length() > 10 && !word.contains("-"))
					.peek(System.out::println)
					.count();
			
			System.out.println("# of words length greater than 4"+wordCount);
		}
	}
	
	public static void main(String[] args) throws IOException {
		ConcatStreams concatStreams = new ConcatStreams();
		concatStreams.collectAllTheWordsFrom4Files();
	}

}
