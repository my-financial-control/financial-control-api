package pedro.almeida.financialcontrol.infra.repositories.nosql.config;

import org.bson.types.Decimal128;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new BigDecimalToDecimal128Converter());
        converters.add(new Decimal128ToBigDecimalConverter());
        return new MongoCustomConversions(converters);
    }

    @Override
    protected String getDatabaseName() {
        return "financialcontrol";
    }

    static class BigDecimalToDecimal128Converter implements Converter<BigDecimal, Decimal128> {
        @Override
        public Decimal128 convert(BigDecimal source) {
            return new Decimal128(source);
        }
    }

    static class Decimal128ToBigDecimalConverter implements Converter<Decimal128, BigDecimal> {
        @Override
        public BigDecimal convert(Decimal128 source) {
            return source.bigDecimalValue();
        }
    }
}
