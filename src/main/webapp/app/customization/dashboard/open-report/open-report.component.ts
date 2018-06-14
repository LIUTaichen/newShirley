import { Component, OnInit, Input } from '@angular/core';
import { Niggle } from '../../../entities/niggle';
import * as moment from 'moment';
import { NiggleUtilService } from '../../niggle-util.service';

@Component({
  selector: 'jhi-open-report',
  templateUrl: './open-report.component.html',
  styleUrls: ['./open-report.component.css']
})
export class OpenReportComponent implements OnInit {

  @Input() niggles: Niggle[];
  displayedColumns = ['weekending', 'total', 'closed', 'opened', 'ageOfOldest'];
  dataSource = ELEMENT_DATA;

  constructor(private niggleUtilService: NiggleUtilService) { }

  ngOnInit() {
    console.log(this.niggleUtilService.getDateOfEndOfCurrentWeek());
  }

}
export interface Element {
  weekending: Date;
  total: number;
  closed: number;
  opened: number;
  ageOfOldest: string;
}

const ELEMENT_DATA: Element[] = [
  {weekending: new Date('2018-06-11'), total: 189, closed: 40 , opened: 20, ageOfOldest: '215 days'},
  {weekending: new Date('2018-06-04'), total: 189, closed: 40 , opened: 20, ageOfOldest: '215 days'},
  {weekending: new Date('2018-05-28'), total: 189, closed: 40 , opened: 20, ageOfOldest: '215 days'},
];
