package com.vin.poc.config;


import com.vin.poc.model.VehicleDTO;
import com.vin.poc.processor.JobCompletionListener;
import com.vin.poc.processor.VehicleFieldSetMapper;
import com.vin.poc.processor.VehicleItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig
{

    private String[] format = new String[]{"vehicleId","VIN","model","year","color","company","category"};

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public VehicleFieldSetMapper vehicleFieldSetMapper;


    @Bean
    public Job processJob(Step step)
    {
        return jobBuilderFactory.get("processJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step).end().build();
    }

    @Bean
    public Step orderStep1(JdbcBatchItemWriter<VehicleDTO> writer)
    {
        return stepBuilderFactory.get("orderStep1").<VehicleDTO, VehicleDTO> chunk(10)
                .reader(flatFileItemReader())
                .processor(employeeItemProcessor())
                .writer(writer).build();
    }

    @Bean
    public FlatFileItemReader<VehicleDTO> flatFileItemReader()
    {
        return new FlatFileItemReaderBuilder<VehicleDTO>()
                .name("flatFileItemReader")
                .resource(new ClassPathResource("input/Book.csv"))
                .delimited()
                .names(format)
                .linesToSkip(1)
                .lineMapper(lineMapper())
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>(){{
                    setTargetType(VehicleDTO.class);
                }})
                .build();
    }

    @Bean
    public LineMapper<VehicleDTO> lineMapper()
    {
        final DefaultLineMapper<VehicleDTO> defaultLineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames(format);

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(vehicleFieldSetMapper);

        return defaultLineMapper;
    }

    @Bean
    public VehicleItemProcessor employeeItemProcessor()
    {
        return new VehicleItemProcessor();
    }

    @Bean
    public JobExecutionListener listener()
    {
        return new JobCompletionListener();
    }

    @Bean
    public JdbcBatchItemWriter<VehicleDTO> writer(final DataSource dataSource)
    {
        return new JdbcBatchItemWriterBuilder<VehicleDTO>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO vehicle(vehicleId, VIN, model, year, color, " +
                        "company, category) VALUES(:vehicleId, :VIN, :model, :year, :color," +
                        " " +
                        ":company, :category)")
                .dataSource(dataSource)
                .build();
    }
}
